import api from './api';
import AsyncStorage from '@react-native-async-storage/async-storage';

export const login = async (email, password) => {
    try {
        const email = 'jswwwwww@naver.com';
        const password = '1234';

        const res = await api.post('/auth/login', { email, password });
        console.log(res);

        const { accessToken, refreshToken } = res.data;

        await AsyncStorage.setItem('accessToken', accessToken);
        await AsyncStorage.setItem('refreshToken', refreshToken);

        // console.log(AsyncStorage)
        //
        return true;
    } catch (err) {
        console.error('로그인 실패:', err);
        throw err;
    }
};

export const logout = async () => {
    try {
        const token = await AsyncStorage.getItem('accessToken');
        if (!token) return;

        await api.delete('/auth/logout', {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });

        await AsyncStorage.removeItem('accessToken'); // 토큰 제거
        console.log('로그아웃 완료');
    } catch (err) {
        console.error('로그아웃 실패:', err);
    }
};

