// apps/mobile/screens/LoginScreen.js
import React, { useState } from 'react';
import { View, TextInput, Button, StyleSheet, Alert } from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { login } from "@api/login";
import axios from 'axios';

export default function LoginScreen({ navigation }) {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const handleLogin = async () => {
        try {
            await login(email, password);

            navigation.replace('Home');
        } catch (err) {
            Alert.alert('로그인 실패', '이메일이나 비밀번호를 다시 확인해줘.');
        }
    };

    return (
        <View style={styles.container}>
            <TextInput
                placeholder="이메일"
                value={email}
                onChangeText={setEmail}
                style={styles.input}
                autoCapitalize="none"
            />
            <TextInput
                placeholder="비밀번호"
                value={password}
                onChangeText={setPassword}
                style={styles.input}
                secureTextEntry
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
    input: {
        borderWidth: 1,
        borderColor: '#999',
        padding: 10,
        marginBottom: 15,
        borderRadius: 5,
    },
});
