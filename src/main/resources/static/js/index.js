$(function () {
    $('#reset').click(() => {
        $.getJSON('reset?machineCode=BJFL000000001&cabinetAddress=1&device=1', null, (response) => {
            console.log(response)
        })
    })
    $('#cell_status').click(() => {
        $.getJSON('cellStatus?machineCode=BJFL000000001&cabinetAddress=1&clearFlag=1', null, (response) => {
            console.log(response)
        })
    })
    $('#shipment').click(() => {
        $.getJSON(`shipment?machineCode=BJFL000000001&cabinetAddress=1&orderCode=MID20180517143330000${parseInt(Math.random() * 100000)}&f=400&b=300&lr=200`, null, (response) => {
            console.log(response)
        })
    })
    $('#shipment_result').click(() => {
        $.getJSON('shipmentResult?machineCode=BJFL000000001&cabinetAddress=1&isDeal=1', null, (response) => {
            console.log(response)
        })
    })
    $('#shipment_log').click(() => {
        $.getJSON('shipmentLog?machineCode=BJFL000000001&cabinetAddress=1&orderCode=&times=1', null, (response) => {
            console.log(response)
        })
    })

    var websocket = null;

    //判断当前浏览器是否支持WebSocket
    if ('WebSocket' in window) {
        websocket = new WebSocket("ws://39.106.102.113:8082/websocket");
    } else {
        alert('Not support websocket')
    }

    //连接发生错误的回调方法
    websocket.onerror = function () {
        appendMessageIntoHtml("error");
    };

    //连接成功建立的回调方法
    websocket.onopen = function (event) {
        appendMessageIntoHtml("open");
    }

    //接收到消息的回调方法
    websocket.onmessage = function (event) {
        appendMessageIntoHtml(event.data);
    }

    //连接关闭的回调方法
    websocket.onclose = function () {
        appendMessageIntoHtml("close");
    }

    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function () {
        websocket.close();
    }

    //将消息显示在网页上
    function appendMessageIntoHtml(innerHTML) {
        document.getElementById('message').innerHTML += innerHTML + '<br/>';
    }

    //关闭连接
    function closeWebSocket() {
        websocket.close();
    }

    function reOpenWebSocket() {
        // 存在问题，重新打开后，与服务端通信正常，但客户端onOpen和onMessage无法触发
        websocket = new WebSocket("ws://localhost:8081/websocket");
        appendMessageIntoHtml('重新连接了')
    }

    //发送消息
    function send() {
        var message = document.getElementById('text').value;
        websocket.send(message);
    }
})