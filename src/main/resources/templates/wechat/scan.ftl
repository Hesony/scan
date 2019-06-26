<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>加载中...</title>
    <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script  type="text/javascript">
        wx.config({
            debug: false,
            appId: 'wxcaf6f6452ceafe6d',
            timestamp:'${timestamp}',
            nonceStr:'${nonceStr}',
            signature:'${signature}',
            jsApiList : [ 'checkJsApi', 'scanQRCode' ]
        });//end_config
        //步骤五
        wx.error(function(res) {
            alert("出错了：" + res.errMsg);
        });
        //步骤四
        wx.ready(function() {
            wx.checkJsApi({
                jsApiList : ['scanQRCode'],
                success : function(res) {
                }
            });

            //扫描二维码
                wx.scanQRCode({
                    needResult : 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
                    scanType : [ "qrCode", "barCode" ], // 可以指定扫二维码还是一维码，默认二者都有
                    success : function(res) {
                        var scancode = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
                        //document.getElementById("wm_id").value = result;//将扫描的结果赋予到jsp对应值上
                        //获取url的openid参数
                        var openid = location.href.split('#')[0].split("?")[1].split("&")[0].split("=")[1];
                        alert("核销码为：" + scancode);
                        //todo:线下测试域名与线上不同，注意！！！
                        window.location.href="http://xiaonan.mynatapp.cc/scancode/youzan/verifyticket?scancode=" + scancode + "&openid=" + openid
                        //window.location.href="http://www.ycssx.cn/scancode/youzan/verifyticket?scancode=" + scancode + "&openid=" + openid
                    }
                });

        });//end_ready
    </script>
</head>
<body>
</body>
</html>