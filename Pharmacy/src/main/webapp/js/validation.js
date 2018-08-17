function validateSignIn(){
    var erLogin = document.getElementById("loginIn"),
        erPassword = document.getElementById("passwordIn");
    erLogin.innerHTML= "";
    erPassword.innerHTML= "";
    var result = true;

    var login=document.forms["LoginForm"]["login"].value;
    var password=document.forms["LoginForm"]["password"].value;

    if (!login){
        erLogin.innerHTML="*please fill this field";
        result = false;
    }
    if (!password){
        erPassword.innerHTML="*please fill this field";
        result = false;
    }
    if (!/\w{5,20}/.test(login)){
        erLogin.innerHTML="*invalid login";
        result = false;
    }
    if(!/\w{5,45}/.test(password)){
        erPassword.innerHTML="*invalid password";
        result = false
    }
    return result;
}


function validateSignUp(){

    var erNewLogin = document.getElementById("loginUp"),
        erNewPassword = document.getElementById("passwordUp"),
        erMail = document.getElementById("mailUp"),
        erFirstName = document.getElementById("firstNameUp"),
        erLastName = document.getElementById("lastNameUp");

    erNewLogin.innerHTML= "";
    erNewPassword.innerHTML= "";
    erMail.innerHTML = "";
    erFirstName.innerHTML = "";
    erLastName.innerHTML = "";

    var login = document.forms["SignUpForm"]["login"].value,
        password = document.forms["SignUpForm"]["password"].value,
        firstName = document.forms["SignUpForm"]["firstName"].value,
        lastName = document.forms["SignUpForm"]["lastName"].value,
        mail = document.forms["SignUpForm"]["email"].value,
        result = true;

    // if (!login){
    //     erNewLogin.innerHTML="*please fill this field";
    //     return false;
    // }
    // if (!password){
    //     erNewPassword.innerHTML="*please fill this field";
    //     return false;
    // }
    // if(!firstName){
    //     erFirstName.innerHTML="*please fill this field";
    //     return false;
    // }
    // if(!lastName){
    //     erLastName.innerHTML="*please fill this field";
    //     return false;
    // }
    // if(!mail){
    //     erMail.innerHTML="*please fill this field";
    //     return false;
    // }
    if (!/\w{5,20}/.test(login)){
        erNewLogin.innerHTML="*invalid login";
        result = false;
    }
    if(!/\w{5,45}/.test(password)){
        erNewPassword.innerHTML="*invalid password";
        result = false;
    }
    if(!/[А-Яа-я]{3,20}/.test(firstName)){
        erFirstName.innerHTML="*invalid first name";
        result = false;
    }
    if(!/[А-Яа-я]{3,25}/.test(firstName)){
        erLastName.innerHTML="*invalid last name";
        result = false;
    }
    if(!/\w{5,20}@\w{3,8}\.\w{2,4}/.test(mail)){
        erMail.innerHTML="*invalid mail";
        result = false;
    }
    return result;
}