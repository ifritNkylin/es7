// >=    >     =      <      <=
function validateInternal(version, opt, optVersion) {
    if (!opt) {
        return true
    }
    //校验操作符
    const opts = ['>=', '>', '=', '<', '<='];
    if (!opts.includes(opt)) {
        throw "opt非法"
    }

    if (!optVersion || !version) {
        throw "version不能为空"
    }

    let versionSplit = version.split(".");
    let optVersionSplit = optVersion.split(".");

    //校验版本格式
    if (versionSplit.length !== 3 || optVersionSplit.length !== 3) {
        return false
    }

    function arrayElementsCanParseNumber(arr) {
        for (let arrKey in arr) {
            if (arrKey.length === 0) {
                return false
            }
            if (isNaN(Number(arrKey))) {
                return false
            }
        }
        return true
    }

    if (!arrayElementsCanParseNumber(versionSplit)
        || !arrayElementsCanParseNumber(optVersionSplit)) {
        throw "version非法"
    }
    if (opt === '=') {
        opt = '=='
    }

    for (let i = 0; i < 3; i++) {
        let maxLen = Math.max(versionSplit[i].length, optVersionSplit[i].length);
        versionSplit[i] = versionSplit[i].padStart(maxLen, '0')
        optVersionSplit[i] = optVersionSplit[i].padStart(maxLen, '0')
    }
    let finalVersion = versionSplit.join(".");
    let finalOptVersionSplit = optVersionSplit.join(".");

    return eval("'" + finalVersion + "'" + opt + "'" + finalOptVersionSplit + "'");
}

console.log(validateInternal("1.1.1", ">", '1.0.12'));
console.log(validateInternal("1.1.1", ">", '1.1.12'));

function validateVersion(version, fromOpt, fromVersion, toOpt, toVersion) {

    let minDependencyRes = validateInternal(version, fromOpt, fromVersion);
    let maxDependencyRes = validateInternal(version, toOpt, toVersion);

    return minDependencyRes && maxDependencyRes
}

// function validateVersion2(version, fromOpt, fromVersion, toOpt, toVersion) {
//     //校验操作符
//     const opts = ['>=', '>', '=', '<', '<='];
//     if ((!!fromOpt && !opts.includes(fromOpt)) || (!!toOpt && !opts.includes(toOpt))) {
//         return false
//     }
//
//     let versionSplit = version.split(".");
//     let fromVersionSplit = fromVersion.split(".");
//     let toVersionSplit = toVersion.split(".");
//     //校验版本格式
//     if (versionSplit.length !== 3 || fromVersionSplit.length !== 3 || toVersionSplit.length !== 3) {
//         return false
//     }
//
//     function arrayElementsCanParseNumber(arr) {
//         for (let arrKey in arr) {
//             if (arrKey.length === 0) {
//                 return false
//             }
//             if (isNaN(Number(arrKey))) {
//                 return false
//             }
//         }
//         return true
//     }
//
//     if (!arrayElementsCanParseNumber(versionSplit)
//         || !arrayElementsCanParseNumber(fromVersionSplit)
//         || !arrayElementsCanParseNumber(toVersionSplit)) {
//         return false
//     }
//
//     if (fromOpt === '=') {
//         fromOpt = '=='
//     }
//     if (toOpt === '=') {
//         toOpt = '=='
//     }
//
//     for (let i = 0; i < 3; i++) {
//         let maxLen = Math.max(versionSplit[i].length, fromVersionSplit[i].length, toVersionSplit[i].length);
//         versionSplit[i] = versionSplit[i].padStart(maxLen, '0')
//         fromVersionSplit[i] = fromVersionSplit[i].padStart(maxLen, '0')
//         toVersionSplit[i] = toVersionSplit[i].padStart(maxLen, '0')
//     }
//     let finalVersion = versionSplit.join(".");
//     let finalFromVersionSplit = fromVersionSplit.join(".");
//     let finalToVersionSplit = toVersionSplit.join(".");
//
//     let minDependencyRes;
//     if (!fromOpt) {
//         minDependencyRes = true;
//     } else {
//         minDependencyRes = eval("'" + finalVersion + "'" + fromOpt + "'" + finalFromVersionSplit + "'");
//     }
//
//     let maxDependencyRes;
//     if (!toOpt) {
//         maxDependencyRes = true;
//     } else {
//         maxDependencyRes = eval("'" + finalVersion + "'" + toOpt + "'" + finalToVersionSplit + "'");
//     }
//     return minDependencyRes && maxDependencyRes
// }


var res = eval("'1.19.0' >= '1.0.56'")

// console.log(validateVersion("1.19.0", ">", null, "<=", "1.20.5"));
console.log(validateVersion("1.19.0", null, null, "<=", "1.20.5"));
console.log(validateVersion("1.19.0", null, "1.0.56", "<=", "1.20.5"));
console.log(validateVersion("1.191.0", null, "1.0.56", "<=", "1.20.5"));
console.log(validateVersion("1.19.0", ">=", "1.0.56", "<=", "1.20.5"));
console.log(validateVersion("1.029.0", ">=", "0.108.5236", "<=", "1.20.5"));
console.log(validateVersion("1.029.0", ">=", "1.108.5236", "<=", "1.20.5"));
console.log(validateVersion("1.029.0", "=", "1.108.5236", "=", "1.20.5"));
console.log(validateVersion("1.029.01", "=", "1.29.1", "=", "01.29.01"));

