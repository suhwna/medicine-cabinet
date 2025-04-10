import React from 'react';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { createDrawerNavigator } from '@react-navigation/drawer';

import LoginScreen from '@screens/LoginScreen';
import HomeScreen from '@screens/HomeScreen';
import SignupScreen from '@screens/SignupScreen';
// import SearchScreen from '@screens/SearchScreen';
// import SettingsScreen from '@screens/SettingsScreen';
// import MedicationListScreen from '@screens/MedicationListScreen';
// import ProfileScreen from '@screens/ProfileScreen';
// import ReminderScreen from '@screens/ReminderScreen';

const Stack = createNativeStackNavigator();
const Tab = createBottomTabNavigator();
const Drawer = createDrawerNavigator();

/**
 *
 * @description 홈 탭
 * @author 개발2팀 정수환
 * @since 2025. 3. 27.
 * @returns {JSX.Element} 홈 탭
 */
function HomeTabs() {
    return (
        <Tab.Navigator initialRouteName="Home">
            <Tab.Screen name="Home" component={HomeScreen} />
            {/*<Tab.Screen name="Search" component={SearchScreen} />*/}
            {/*<Tab.Screen name="Settings" component={SettingsScreen} />*/}
        </Tab.Navigator>
    );
}

/**
 *
 * @description 루트 네비게이터 컴포넌트
 * @author 개발2팀 정수환
 * @since 2025. 3. 27.
 * @return {JSX.Element} 루트 네비게이터
 */
function RootNavigator() {
    return (
        <Stack.Navigator initialRouteName="Login">
            {/* 로그인 화면 */}
            <Stack.Screen name="Login" component={LoginScreen} options={{ headerShown: false }} />

            {/* 회원가입 화면 */}
            <Stack.Screen name="Signup" component={SignupScreen} options={{ headerShown: false }} />

            {/* 로그인 성공 후 홈 화면으로 이동 */}
            <Stack.Screen name="Home" component={HomeScreen} />

            {/* 약 목록, 약 등록, 복용 기록 등 */}
            {/*<Stack.Screen name="MedicationList" component={MedicationListScreen} />*/}
            {/*<Stack.Screen name="MedicationDetails" component={MedicationDetailsScreen} />*/}

            {/* 프로필, 알림 관리 등 */}
            {/*<Stack.Screen name="Profile" component={ProfileScreen} />*/}
            {/*<Stack.Screen name="Reminder" component={ReminderScreen} />*/}
        </Stack.Navigator>
    );
}

export default RootNavigator;