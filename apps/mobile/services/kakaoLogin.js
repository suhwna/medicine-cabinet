import React, { useEffect } from 'react';
import { Button, Alert } from 'react-native';
import * as AuthSession from 'expo-auth-session';
import AsyncStorage from "@react-native-async-storage/async-storage";
import api from '@services/api';

const REST_API_KEY = '855708ba40c928ec8bb366b3c674ef9f';
const REDIRECT_URI = "https://auth.expo.io/@jswwwwww/medicine-box";

const discovery = {
    authorizationEndpoint: 'https://kauth.kakao.com/oauth/authorize',
    tokenEndpoint: 'https://kauth.kakao.com/oauth/token',
};

export default function KakaoLoginButton({ onSuccess }) {
    const [request, response, promptAsync] = AuthSession.useAuthRequest(
        {
            clientId: REST_API_KEY,
            redirectUri: REDIRECT_URI,
            responseType: 'code',
        },
        discovery
    );

    useEffect(() => {
        const handleKakaoLogin = async () => {
            if (response?.type === 'success') {
                const code = response.params.code;
                console.log('✅ Kakao auth code:', code);

                try {
                    const res = await api.post('/auth/kakao-login', { code });
                    const { accessToken, refreshToken } = res.data;

                    await AsyncStorage.setItem('accessToken', accessToken);
                    await AsyncStorage.setItem('refreshToken', refreshToken);

                    if (onSuccess) onSuccess();
                } catch (err) {
                    console.error('❌ 카카오 로그인 실패:', err);
                    Alert.alert('카카오 로그인 실패', '다시 시도해주세요.');
                }
            }
        };

        handleKakaoLogin();
    }, [response]);

    return (
        <Button title="카카오 로그인" onPress={() => promptAsync()} />
    );
}
