function componentToHex(number) {
    var hex = Number(number).toString(16);
    if (hex.length < 2) {
        hex = "0" + hex;
    }
    return hex;
}
function rgbToHex(r, g, b) {
    return "#" + componentToHex(r) + componentToHex(g) + componentToHex(b);
}
function showResult(rgb,result,rgbView)
{
    rgbView.innerHTML = rgb;
    result.style.backgroundColor = rgb;
}

window.onload = function () {
    var red = document.getElementById("red");
    var valueRed = document.getElementById("red-value");
    var blue = document.getElementById("blue");
    var valueBlue = document.getElementById("blue-value");
    var green = document.getElementById("green");
    var valueGreen = document.getElementById("green-value");
    var rgbText = document.getElementById("rgb-text");
    var result = document.getElementById("result");

    valueGreen.innerHTML = green.value;
    valueBlue.innerHTML = blue.value;
    valueRed.innerHTML = red.value;
    rgbText.innerHTML = rgbToHex(red.value, green.value, blue.value)

    red.oninput = function () {
        valueRed.innerHTML = red.value;
        showResult( rgbToHex(red.value, green.value, blue.value),result,rgbText)
    }
    blue.oninput = function () {      
        valueBlue.innerHTML = blue.value;
        showResult( rgbToHex(red.value, green.value, blue.value),result,rgbText)
    }

    green.oninput = function () {
        valueGreen.innerHTML = green.value;
        showResult( rgbToHex(red.value, green.value, blue.value),result,rgbText)
       }
};
