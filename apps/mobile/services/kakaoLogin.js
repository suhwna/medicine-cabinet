import * as AuthSession from 'expo-auth-session';

export default async function kakaoLogin() {
    const redirectUri = 'https://auth.expo.io/@jswwwwww/medicine-box';

    const request = new AuthSession.AuthRequest({
        clientId: '855708ba40c928ec8bb366b3c674ef9f',
        responseType: 'code',
        redirectUri,
        scopes: [],
    });

    const result = await request.promptAsync(
        {
            authorizationEndpoint: 'https://kauth.kakao.com/oauth/authorize',
            tokenEndpoint: 'https://kauth.kakao.com/oauth/token',
        },
        {
            useProxy: true,
            preferEphemeralSession: false
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
