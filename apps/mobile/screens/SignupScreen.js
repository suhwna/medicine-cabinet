import React, { useState } from 'react';
import { View, TextInput, Button, StyleSheet, Alert } from 'react-native';
import axios from 'axios';

export default function SignupScreen({ navigation }) {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [name, setName] = useState('');

    const handleSignup = async () => {
        if (!email || !password || !name) {
            Alert.alert('입력 오류', '모든 필드를 입력해주세요.');
            return;
        }

        try {
            await axios.post('http://localhost:8080/users', {
                email,
                password,
                name,
            });
            Alert.alert('회원가입 완료', '로그인 화면으로 이동합니다.', [
                { text: '확인', onPress: () => navigation.replace('Login') },
            ]);
        } catch (err) {
            Alert.alert('회원가입 실패', '이미 존재하는 이메일일 수 있습니다.');
        }
    };

    return (
        <View style={styles.container}>
            <TextInput
                placeholder="이름"
                value={name}
                onChangeText={setName}
                style={styles.input}
            />
            <TextInput
                placeholder="이메일"
                value={email}
                onChangeText={setEmail}
                autoCapitalize="none"
                style={styles.input}
            />
            <TextInput
                placeholder="비밀번호"
                value={password}
                onChangeText={setPassword}
                secureTextEntry
                style={styles.input}
            />
            <Button title="회원가입" onPress={handleSignup} />
            <View style={{ marginTop: 10 }}>
                <Button
                    title="로그인으로 돌아가기"
                    onPress={() => navigation.replace('Login')}
                />
            </View>
        </View>
    );
}

const styles = StyleSheet.create({
    container: { padding: 20, flex: 1, justifyContent: 'center' },
    input: {
        borderWidth: 1,
        borderColor: '#aaa',
        marginBottom: 15,
        padding: 10,
        borderRadius: 5,
    },
});
