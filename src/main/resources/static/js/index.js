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
        $.getJSON('shipmentLog?orderCode=&cabinetAddress=1&times=1', null, (response) => {
            console.log(response)
        })
    })
})