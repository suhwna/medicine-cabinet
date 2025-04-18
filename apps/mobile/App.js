// import * as WebBrowser from 'expo-web-browser';
// WebBrowser.maybeCompleteAuthSession(); // 웹 브라우저 세션을 완료하는 함수
import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import RootNavigator from "@navigation/RootNavigator";

function App() {
    return (
        <NavigationContainer>
            <RootNavigator/>
        </NavigationContainer>
    );
}

export default App;