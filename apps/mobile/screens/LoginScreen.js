import React, { useState } from 'react';
import { View, TextInput, Button, StyleSheet, Text, Alert } from 'react-native';
import { useNavigation } from '@react-navigation/native'; // 네비게이션 사용
import api from '@services/api';
import AsyncStorage from "@react-native-async-storage/async-storage"; // 토큰 저장할 때 사용

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
            <Button title="로그인" onPress={handleLogin} />
            <Button
                title="회원가입"
                onPress={() => navigation.replace('Signup')}
            />
        </View>
    );
}

const styles = StyleSheet.create({
    container: { flex: 1, justifyContent: 'center', padding: 20 },
    input: { borderWidth: 1, borderColor: '#ccc', marginBottom: 10, padding: 8 },
});
