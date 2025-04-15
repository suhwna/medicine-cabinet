import React, { useState } from 'react';
import { View, TextInput, Button, StyleSheet, Text, Alert, Image } from 'react-native';
import { useNavigation } from '@react-navigation/native'; // 네비게이션 사용
import api from '@services/api';
import AsyncStorage from "@react-native-async-storage/async-storage"; // 토큰 저장할 때 사용
import kakaoLogin from '@services/kakaoLogin'; // 카카오 로그인 서비스

export default function LoginScreen({ navigation }) {
    const [email, setEmail] = useState('jswwwwww@naver.com');
    const [password, setPassword] = useState('1234');

    const handleLogin = async () => {
        try {
            // 이메일과 비밀번호가 입력되지 않았을 때
            if (!email || !password) {
                Alert.alert('로그인 실패', '이메일과 비밀번호를 모두 입력하세요.');
                return;
            }

            const res = await api.post('/auth/login', { email, password }); // 로그인 요청
            const { accessToken, refreshToken } = res.data;

            await AsyncStorage.setItem('accessToken', accessToken);
            await AsyncStorage.setItem('refreshToken', refreshToken);

            navigation.replace('Home');
        } catch (err) {
            Alert.alert('로그인 실패', '이메일 또는 비밀번호를 확인해주세요.');
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

            {/* 입력 */}
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

            {/* 버튼 */}
            <View style={styles.buttonWrap}>
                <Button title="로그인" onPress={handleLogin} />
                <View style={{ marginTop: 10 }} />
                <Button title="카카오 로그인" onPress={kakaoLogin} />
                <View style={{ marginTop: 10 }} />
                <Button
                    title="회원가입"
                    onPress={() => navigation.replace('Signup')}
                    color="#6c757d"
                />
            </View>
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
