import * as AuthSession from 'expo-auth-session';
import * as WebBrowser from 'expo-web-browser';
import { Platform } from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';
import api from '@services/api';
import { useEffect } from 'react';

WebBrowser.maybeCompleteAuthSession();

const handleKakaoLogin = async () => {
    try {
        const KAKAO_REST_API_KEY = '855708ba40c928ec8bb366b3c674ef9f';

        const discovery = {
            authorizationEndpoint: 'https://kauth.kakao.com/oauth/authorize',
            tokenEndpoint: 'https://kauth.kakao.com/oauth/token',
        };

        const REDIRECT_URI = AuthSession.makeRedirectUri({
            useProxy: true,
            native: `medicinebox://redirect`,  // 만약 커스텀 스킴도 쓰는 경우
        });


        // const REDIRECT_URI = 'https://auth.expo.io/@jswwwwww/medicine-box';

        console.log('Platform:', Platform.OS);

        console.log('🔗 redirect URI:', REDIRECT_URI);


        // const request = new AuthSession.AuthRequest({
        //     clientId: KAKAO_REST_API_KEY,
        //     responseType: AuthSession.ResponseType.Code,
        //     scopes: [],
        //     redirectUri: REDIRECT_URI,
        // });
        //
        // console.log('카카오 로그인 시작');


        // console.log(REDIRECT_URI)


        // const result = await request.promptAsync(discovery, { useProxy: true });

        // console.log(result);


        // if (result.type === 'success' && result.params.code) {
        //     const code = result.params.code;
        //
        //     console.log(code)
        //
        //     // axios 인스턴스로 백엔드에 로그인 요청
        //     // const res = await api.post('/auth/kakao', {
        //     //     code,
        //     //     redirectUri: REDIRECT_URI
        //     // });
        //     //
        //     // const { accessToken, refreshToken } = res.data;
        //     //
        //     // await AsyncStorage.setItem('accessToken', accessToken);
        //     // await AsyncStorage.setItem('refreshToken', refreshToken);
        //
        //     console.log('카카오 로그인 완료!');
        // } else {
        //     console.log('카카오 로그인 취소 또는 실패');
        // }
    } catch (err) {
        console.error('카카오 로그인 실패:', err);
    }
};

export default handleKakaoLogin;