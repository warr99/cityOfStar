let button = document.querySelector('.button');
let ball = document.querySelector('.ball');
let bottom = document.querySelector('.bottom');
let off = document.querySelector('.off');
let on = document.querySelector('.on');
let index = 0;
let signInBtn = document.querySelector('.signInBtn');
let signInput = document.querySelector('.signInput');
let sign = document.querySelector('.sign')
button.addEventListener('click',()=>{
    if(index==0){
        index = 1;
        //按钮里的球滑到右边
        ball.style.left = 61 + '%';
        //注册字体变明显，登录字体变淡
        on.style.opacity = 1;
        off.style.opacity = .5;
        //登录卡旋转到背面
        bottom.style.transform = "rotateY(180deg)";
    }else{
        index = 0;
        //按钮里的球滑到左边
        ball.style.left = 0 + '%';
        //登录字体变明显，注册字体变淡
        on.style.opacity = .5;
        off.style.opacity = 1;
        //登录卡旋转到背面
        bottom.style.transform = "rotateY(0)";
    }
})

//单击登录按钮
signInBtn.onclick = function(){
    let a = signInput.value;
    let b = sign.value;

        sealAjax({
        url: 'http://localhost:8080/user/login',
        type:'post',
        data:{username:a,password:b}
    }).then(res =>{
        if(res.code === 200){
            window.location.href = 'http://localhost:8080/static/html/success.html';
        }


    })
}
