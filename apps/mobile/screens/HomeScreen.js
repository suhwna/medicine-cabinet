import React, { useEffect, useState } from 'react';
import { View, Text, Button } from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';
import api from '@services/api';

export default function HomeScreen({ navigation }) {
    // const [user, setUser] = useState(null);
    //
    // const getUser = async () => {
    //     try {
    //         const res = await api.get('/mbr/{email}');
    //         setUser(res.data);
    //     } catch (err) {
    //         console.log(err);
    //     }
    // };
    //
    // useEffect(() => {
    //
    // }, []);

    /**
     *
     * @description 로그아웃
     * @author 개발2팀 정수환
     * @since 2025-04-15
     */
    const logout = async () => {
        try {
            const res = await api.delete('/auth/logout');

            await AsyncStorage.removeItem('accessToken');
            await AsyncStorage.removeItem('refreshToken');

            navigation.replace('Login');
        } catch (err) {
            console.log('로그아웃 실패:', err);
        }
    };

    return (
        <View style={{ padding: 20 }}>
            <Text style={{ fontSize: 20, marginBottom: 20 }}>
            ㅎㅇ
            </Text>
            <Button title="로그아웃" onPress={logout} />
            
        </View>
    );
}
