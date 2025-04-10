import * as AuthSession from 'expo-auth-session';
import * as WebBrowser from 'expo-web-browser';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { Button, View } from 'react-native';
import axios from 'axios';

WebBrowser.maybeCompleteAuthSession(); // 필수 호출

const KAKAO_REST_API_KEY = '855708ba40c928ec8bb366b3c674ef9f'
const REDIRECT_URI = AuthSession.makeRedirectUri({ useProxy: true });

const handleKakaoLogin = async () => {
    try {
        const authUrl = `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${KAKAO_REST_API_KEY}&redirect_uri=${encodeURIComponent(REDIRECT_URI)}`;
        const result = await AuthSession.startAsync({ authUrl });

        if (result.type === 'success' && result.params.code) {
            const code = result.params.code;

            const res = await axios.post('https://your-api.com/auth/oauth/kakao', {
                code,
                redirectUri: REDIRECT_URI,
            });

            const { accessToken, refreshToken } = res.data;
            await AsyncStorage.setItem('accessToken', accessToken);
            await AsyncStorage.setItem('refreshToken', refreshToken);

            console.log('카카오 로그인 완료!');
        }
    } catch (err) {
        console.error('카카오 로그인 실패:', err);
    }
};