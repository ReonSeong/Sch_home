/**
 * File Name : .js
 * Updated Date     Version     User        Change Log
 * 2025-05-03           0.1     ReonQ       Published
 *
 * Now Version : 0.1
 *
 * Description:
 * JS logger
 */

const Logger = (() => {
    // 1. 레벨 정의 및 설정
    const config = {
        levels: {
            error: 0,
            warn: 1,
            info: 2,
            http: 3,
            verbose: 4,
            debug: 5,
            silly: 6,
        },
        // 현재 출력 기준 레벨 (이 값보다 높은 숫자의 레벨은 무시됨)
        // 예: 'info'로 설정하면 error, warn, info만 출력
        currentLevel: 'silly',

        colors: {
            error: 'color: #ff0000; font-weight: bold;',
            warn: 'color: #ffa500; font-weight: bold;',
            info: 'color: #008000;',
            http: 'color: #800080;',
            verbose: 'color: #008080;',
            debug: 'color: #0000ff;',
            silly: 'color: #808080;',
        }
    };

    // Time formating
    const getTimestamp = () => new Date().toISOString().replace('T', ' ').split('.')[0];

    const log = (level, message, data = null) => {
        // 필터링 로직: 설정된 레벨보다 숫자가 크면 출력 안 함
        if (config.levels[level] > config.levels[config.currentLevel]) return;

        const timestamp = getTimestamp();
        const levelTag = level.toUpperCase();

        // 콘솔 출력 (브라우저/Node 공용 스타일)
        console.log(
            `%c[${timestamp}] [${levelTag}] %c${message}`,
            config.colors[level],
            'color: inherit;',
            data ? data : ''
        );

        // for later, if need to save
        return {
            timestamp,
            level,
            message,
            data
        };
    };

    return {
        // filter lvl
        setLevel: (newLevel) => {
            if (config.levels[newLevel] !== undefined) config.currentLevel = newLevel;
        },
        error: (msg, data) => log('error', msg, data),
        warn: (msg, data) => log('warn', msg, data),
        info: (msg, data) => log('info', msg, data),
        http: (msg, data) => log('http', msg, data),
        verbose: (msg, data) => log('verbose', msg, data),
        debug: (msg, data) => log('debug', msg, data),
        silly: (msg, data) => log('silly', msg, data),
    };
})();