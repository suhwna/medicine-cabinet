import React, { useState } from 'react';
import { View, TextInput, Button, StyleSheet, Text, Alert, Image, Modal } from 'react-native';
import api from '@services/api';
import AsyncStorage from "@react-native-async-storage/async-storage";
import KakaoLogin from '@services/kakaoLogin';

export default function LoginScreen({ navigation }) {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [showKakao, setShowKakao] = useState(false); // 모달 제어

    const handleLogin = async () => {
        if (!email || !password) {
            Alert.alert('로그인 실패', '이메일과 비밀번호를 입력하세요.');
            return;
        }
        try {
            const res = await api.post('/auth/login', { email, password });
            const { accessToken, refreshToken } = res.data;

            await AsyncStorage.setItem('accessToken', accessToken);
            await AsyncStorage.setItem('refreshToken', refreshToken);

            navigation.replace('Home');
        } catch (err) {
            Alert.alert('로그인 실패', '이메일 또는 비밀번호를 확인해주세요.');
        }
    };

    const handleKakaoSuccess = async (code) => {
        try {
            const response = await api.post('/auth/kakao-login', { code });
            const { accessToken, refreshToken } = response.data;

            await AsyncStorage.setItem('accessToken', accessToken);
            await AsyncStorage.setItem('refreshToken', refreshToken);

            setShowKakao(false);
            navigation.replace('Home');
        } catch (error) {
            Alert.alert('카카오 로그인 실패', '문제가 발생했습니다.');
            setShowKakao(false);
        }
    };

    return (
        <View style={styles.container}>
            <View style={styles.top}>
                <Image
                    source={{ uri: 'https://cdn-icons-png.flaticon.com/512/4320/4320357.png' }}
                    style={styles.logo}
                    resizeMode="contain"
                />
                <Text style={styles.title}>약먹을시간이야!</Text>
            </View>

            <TextInput
                value={email}
                onChangeText={setEmail}
                placeholder="이메일"
                style={styles.input}
                autoCapitalize="none"
            />
            <TextInput
                value={password}
                onChangeText={setPassword}
                placeholder="비밀번호"
                secureTextEntry
                style={styles.input}
            />

            <View style={styles.buttonWrap}>
                <Button title="로그인" onPress={handleLogin} />
                <View style={{ marginTop: 10 }} />
                <Button title="카카오 로그인" onPress={() => setShowKakao(true)} />
                <View style={{ marginTop: 10 }} />
                <Button
                    title="회원가입"
                    onPress={() => navigation.replace('Signup')}
                    color="#6c757d"
                />
            </View>

            {/* 카카오 로그인 WebView를 모달로 */}
            <Modal visible={showKakao} animationType="slide">
                <KakaoLogin
                    onLoginSuccess={handleKakaoSuccess}
                    onClose={() => setShowKakao(false)}
                />
            </Modal>
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        paddingHorizontal: 24,
        backgroundColor: '#ffffff',
    },
    top: {
        alignItems: 'center',
        marginBottom: 24,
    },
    logo: {
        width: 60,
        height: 60,
        marginBottom: 8,
    },
    title: {
        fontSize: 22,
        fontWeight: 'bold',
        color: '#222',
        letterSpacing: 0.5,
    },
    input: {
        width: '100%',
        borderWidth: 1,
        borderColor: '#ced4da',
        borderRadius: 8,
        padding: 12,
        fontSize: 16,
        marginBottom: 16,
        backgroundColor: '#f8f9fa',
    },
    buttonWrap: {
        width: '100%',
        marginTop: 10,
    },
});
