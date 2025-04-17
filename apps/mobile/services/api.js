import axios from 'axios';
import AsyncStorage from '@react-native-async-storage/async-storage'; // 토큰 저장할 때 사용

// axios 인스턴스 생성
const api = axios.create({
    // baseURL: 'http://192.168.0.29:8080',
    baseURL: 'http://192.168.219.112:8080' // mac 내부 ip
});

const kakaoJSKey = '855708ba40c928ec8bb366b3c674ef9f';
const kakaoNative = 'ce5a83e164dc5c8bfaf723be677f1b31'

// 요청마다 토큰 자동 추가하는 인터셉터
api.interceptors.request.use(async config => {
    const token = await AsyncStorage.getItem('accessToken'); // 토큰 가져오기
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

let isRefreshing = false; // 토큰 갱신 중인지 여부
let failedQueue = []; // 갱신 중인 동안 실패한 요청들을 저장할 배열

// 갱신 완료 후 실패한 요청들을 다시 보내는 함수
const processQueue = (error, token = null) => {
    failedQueue.forEach((prom) => {
        if (error) prom.reject(error);
        else prom.resolve(token);
    });
    failedQueue = [];
};

// 응답 인터셉터
api.interceptors.response.use(
    res => res,
    async err => {
        const originalRequest = err.config; // 실패한 요청

        if (err.response?.status === 401 && !originalRequest._retry) {
            originalRequest._retry = true; // 재요청 여부 플래그

            // 토큰 갱신 중이라면
            if (isRefreshing) {
                return new Promise((resolve, reject) => {
                    failedQueue.push({ resolve, reject });
                }).then((token) => {
                    originalRequest.headers.Authorization = 'Bearer ' + token;
                    return api(originalRequest);
                });
            }

            isRefreshing = true;

            try {
                const refreshToken = await AsyncStorage.getItem('refreshToken');
                const res = await axios.post('/auth/refresh', {}, {
                    headers: {
                        Authorization: `Bearer ${refreshToken}`
                    }
                });

                const newAccessToken = res.data.accessToken;
                await AsyncStorage.setItem('accessToken', newAccessToken);
                processQueue(null, newAccessToken);

                originalRequest.headers.Authorization = `Bearer ${newAccessToken}`;
                return api(originalRequest);
            } catch (refreshErr) {
                processQueue(refreshErr, null);
                // 로그아웃 처리 or 로그인 화면으로 이동
                await AsyncStorage.removeItem('accessToken');
                await AsyncStorage.removeItem('refreshToken');
                // 예: navigation.navigate('Login');
                return Promise.reject(refreshErr);
            } finally {
                isRefreshing = false;
            }
        }

        return Promise.reject(err);
    }
);

export default api;
