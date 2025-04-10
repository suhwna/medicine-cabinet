import React, { useState } from 'react';
import { View, TextInput, Button, StyleSheet, Text, Alert } from 'react-native';
import { useNavigation } from '@react-navigation/native';

export default function SignupScreen() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [name, setName] = useState('');
    const navigation = useNavigation();

    // 회원가입 처리 함수
    const handleSignup = async () => {
        if (!email || !password || !name) {
            Alert.alert('회원가입 실패', '모든 필드를 입력해주세요.');
            return;
        }

        // 실제 회원가입 API 호출 후 처리

        // 회원가입 성공 시 로그인 화면으로 돌아가기
        navigation.replace('Login');
    };

    return (
        <View style={styles.container}>
            <TextInput
                style={styles.input}
                placeholder="이름"
                value={name}
                onChangeText={setName}
            />
            <TextInput
                style={styles.input}
                placeholder="이메일"
                value={email}
                onChangeText={setEmail}
                keyboardType="email-address"
                autoCapitalize="none"
            />
            <TextInput
                style={styles.input}
                placeholder="비밀번호"
                value={password}
                onChangeText={setPassword}
                secureTextEntry
            />
            <Button title="회원가입" onPress={handleSignup} />
            <Text style={styles.loginText} onPress={() => navigation.navigate('Login')}>
                이미 계정이 있나요? 로그인
            </Text>
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        padding: 20,
    },
    input: {
        borderWidth: 1,
        borderColor: '#ccc',
        marginBottom: 20,
        padding: 10,
        borderRadius: 5,
    },
    loginText: {
        marginTop: 20,
        color: '#0066cc',
        textAlign: 'center',
    },
});