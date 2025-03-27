import React, { useEffect, useState } from 'react';
import { View, Text, Button } from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';
import api from '@services/api';

export default function HomeScreen({ navigation }) {
    const [user, setUser] = useState(null);

    // const getUser = async () => {
    //     try {
    //         const res = await api.get('/mbr/{email}');
    //         setUser(res.data);
    //     } catch (err) {
    //         console.log(err);
    //     }
    // };

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

    useEffect(() => {

    }, []);

    return (
        <View style={{ padding: 20 }}>
            <Text style={{ fontSize: 20, marginBottom: 20 }}>
            ㅎㅇ
            </Text>
            <Button title="로그아웃" onPress={logout} />
        </View>
    );
}
