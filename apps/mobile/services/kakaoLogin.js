import * as AuthSession from 'expo-auth-session';
import * as WebBrowser from 'expo-web-browser';
import AsyncStorage from '@react-native-async-storage/async-storage';
import axios from 'axios';
import { useEffect } from 'react';

WebBrowser.maybeCompleteAuthSession(); // 필수 호출

const KAKAO_REST_API_KEY = '855708ba40c928ec8bb366b3c674ef9f'
const REDIRECT_URI = AuthSession.makeRedirectUri({ useProxy: true });

const handleKakaoLogin = async () => {
    try {
        console.log('카카오 로그인 시작');

        const authUrl = `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${KAKAO_REST_API_KEY}&redirect_uri=${encodeURIComponent(REDIRECT_URI)}`;
        const result = await AuthSession.startAsync({ authUrl });

        console.log(result);

        if (result.type === 'success' && result.params.code) {
            const code = result.params.code;

            const res = await api.post('/auth/kakao', {
                code,
                redirectUri: REDIRECT_URI,
            });

            console.log(res);

            const { accessToken, refreshToken } = res.data;
            await AsyncStorage.setItem('accessToken', accessToken);
            await AsyncStorage.setItem('refreshToken', refreshToken);

            console.log('카카오 로그인 완료!');
        }
    } catch (err) {
        console.error('카카오 로그인 실패:', err);
    }
};

export default handleKakaoLogin;