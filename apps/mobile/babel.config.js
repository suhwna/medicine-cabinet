module.exports = function (api) {
    api.cache(true);
    return {
        presets: ['babel-preset-expo'],
        plugins: [
            [
                'module-resolver',
                {
                    alias: {
                        '@components': './components',
                        '@constants': './constants',
                        '@screens': './screens',
                        '@services': './services',
                        '@store': './store',
                        '@styles': './styles'
                    },
                },
            ],
        ],
    };
};
