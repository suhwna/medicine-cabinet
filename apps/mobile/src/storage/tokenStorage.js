import AsyncStorage from '@react-native-async-storage/async-storage';

export async function storeToken(token) {
    await AsyncStorage.setItem('jwtToken', token);
}
