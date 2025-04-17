import * as AuthSession from 'expo-auth-session';
import * as Linking from 'expo-linking';

// Android, dev-client 기준 redirect URI 생성
const redirectUri = AuthSession.makeRedirectUri({
    native: 'medicinebox://redirect',  // 앱 스킴과 매칭되어야 함
    useProxy: false,
});

export default async function kakaoLogin() {
    const request = new AuthSession.AuthRequest({
        clientId: '855708ba40c928ec8bb366b3c674ef9f',
        responseType: 'code',
        redirectUri,
        scopes: [],
    });

    await request.makeAuthUrlAsync(); // 내부적으로 URL 준비

    const result = await request.promptAsync(
        {
            authorizationEndpoint: 'https://kauth.kakao.com/oauth/authorize',
            tokenEndpoint: 'https://kauth.kakao.com/oauth/token',
        },
        {
            useProxy: false,
        }
    );

    console.log('🔥 카카오 로그인 결과:', result);

    if (result.type === 'success') {
        const code = result.params.code;
        alert(`인가코드: ${code}`);
        // 여기서 access_token 교환 로직 넣으면 됨
    } else {
        alert('카카오 로그인 실패함!');
    }
}