function validateForm(){
    var name = document.getElementById("firstname").value;
    var lastname = document.getElementById("lastname").value;
    var password = document.getElementById("password").value;
    var c_password = document.getElementById("confirm_password").value;
    var username = document.getElementById("username").value; //between 4 - 20 char
    var email = document.getElementById("email").value;

    const mail_format = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    var error = document.getElementById('alert-value');

    console.log(name)
    console.log(lastname)

    if(name === '' || lastname === '' || password === '' || c_password === '' || email === '' || username === ''){
        error.innerHTML = 'please input all the information!'
        return false;
    }
    if(password.length < 8){
        error.innerHTML = 'password has to be at least 8 characters!'
        return false;
    }
    if(password.localeCompare(c_password) != 0){
        error.innerHTML = 'password and confirm password are incorrect!'
        return false;
    }
    else if(username.length < 4){
        error.innerHTML = 'username is too short!'
        return false;
    }
    else if(username.length > 20){
        error.innerHTML = 'username is too long!'
        return false;
    }
    else if(!(email.match(mail_format))){
        error.innerHTML = 'invalid email!'
        return false;
    }
    else {return true;}
}