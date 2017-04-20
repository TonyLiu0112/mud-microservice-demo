## API网关服务

> 网关服务提供给移动端使用，安全性方面考虑使用API密钥的方式保证安全交互

## js操作

1.  通过Ajax获取token


    $.ajax({
      url: 'http://localhost:9998/security/doLogin',
      dataType:"json",
      contentType: 'application/json',
      type:'post',
      data:JSON.stringify({
        clientId: 'acme',
        secret: 'acmesecret',
        loginName: 'admin',
        password: 'admin'
      }),
      success:function(data){
        console.log(data)
      },
      error:function(){
    
      }
    });

返回信息中包含access_token信息

2. 通过Ajax访问受保护资源


    $.ajax({
      url: 'http://localhost:7000/demo/tokens',
      dataType:"json",
      contentType: 'application/json',
      type:'get',
      headers: {
        Authorization: "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwib3JnYW5pemF0aW9uIjoiYWNtZVpWWXYiLCJleHAiOjE0OTI3MjQ0ODgsImp0aSI6IjY3MzEzOTBjLWU3NGYtNDZkZi1hMDRjLTc3YTIxNjhiNWQxZCIsImNsaWVudF9pZCI6ImFjbWUifQ.l231Gxp6bn-M-gBt4Rd_hCRLPpCSLG-yimC-wAIfgr55h1y7w71V6Rn5MzZIBD80UGKywr-5-S1Z0LhKIk-TkMMnuRVr-UOlwCfHMIVoXL9qrmP9jfCVU5SvzqKkLKJMijDkno1rrncHg0_OFHVJvLd35iSZr-xwhA6smlmmPmtOIWfPRMZJTxdDzL018ev5hnc0WZW44L4K65fBSNDLYnEEJxnobANdeb8lotovVU4f92HOLrAXfgTZxc9sfNPm-181GUsrtXKaixLeTcJyIixJUVkwN9yGu7VZW6ndUU1pl5vbj9laI9BZRqVY-j7z7l7QHul8l_-PuKfYKeCGmQ"
      },
      success:function(data){
        console.log(data)
      },
      error:function(){
    
      }
    });
