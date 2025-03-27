import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import RootNavigator from '@navigation/RootNavigator';  // 네비게이션 분리

function App() {
    return (
        <NavigationContainer>
            <RootNavigator />  {/* 분리된 네비게이션 사용 */}
        </NavigationContainer>
    );
}

export default App;