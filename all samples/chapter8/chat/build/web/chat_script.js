var wsUri = getServerRootUri() + "/chat/chat-server";
var currentUsername = "";
var chatWebsocket;

function getServerRootUri() {
    return "ws://" + (document.location.hostname == "" ? "localhost" : document.location.hostname) + ":" +
        (document.location.port == "" ? "8080" : document.location.port);
}

function init() {
    connect();
    refreshForSignInOut();  
}


function connect() {  
    if (chatWebsocket == null) {
        chatWebsocket = new WebSocket(wsUri, "chat-session");
        chatWebsocket.onmessage = function (evt) {
        onMessage(evt)
        };
        chatWebsocket.onerror = function (evt) {
            alert("Error: " + evt.data);
        };
        chatWebsocket.onclose = function (evt) {
            onClose(evt);
        }
    }
}

function signin() {
    connect();
    var retVal = prompt("Please enter your name : ", "user-name", "");
    if (retVal != "" & retVal != null) {
        currentUsername = retVal;
        chatWebsocket.send("unmsg" + currentUsername);
    } 
    
}

function onMessage(evt) {
    var mString = evt.data.toString();
    if (mString.search("unmsg") == 0) {
        currentUsername = mString.substring(5, mString.length);
        refreshForSignInOut();
    }
    if (mString.search("ctmsg") == 0) {
        var transcriptUpdate = mString.substring(6, mString.length);
        writeTranscript(transcriptUpdate);
    }
    if (mString.search("ulupd") == 0) {
        var updateString = mString.substring(6, mString.length);
        writeUserlist(updateString);
    }
}

function onClose(evt) {
    currentUsername = "";
    chatWebsocket = null;
    refreshForSignInOut();
}

function isSignedIn() {
    return (currentUsername != "");
}

function button_signInOut() {
    if (isSignedIn()) {
        chatWebsocket.send("sorq" + currentUsername);
    } else {
        signin();
    }
}

function button_sendMessage() {
    var chatString = chatMessageTextID.value;
    if (chatString.length > 0) {
        chatWebsocket.send("ctmsg" + currentUsername + ":" + chatString);
        chatMessageTextID.value = "";
    }
}

function refreshForSignInOut() {
    var newTitle = "Chat";
    if (isSignedIn()) {
        newTitle = newTitle + " " + currentUsername;
        SendButtonID.disabled = false;
        chatMessageTextID.disabled = false;
        SignInButtonID.value = "Sign out";
        usernameID.textContent = "You are signed in as " + currentUsername;
        moreID.innerHTML="<a href='data.jsp'>Find out more</a> about where the data is held";
    } else {
        SendButtonID.disabled = true;
        chatMessageTextID.disabled = true;
        SignInButtonID.value = "Sign in";
        transcriptID.textContent = "";
        userListID.textContent = "";
        usernameID.textContent = "";
        moreID.innerHTML="";
    }
    var titleNode = document.getElementById("titleID");
    titleNode.textContent = newTitle;
}

function writeTranscript(str)  {
    var index = str.search(":");
    var currentUsername = str.substring(0, index);
    var message = str.substring(index+1, str.length);
    transcriptID.textContent = transcriptID.textContent + "\n" + currentUsername + "> " + message;
}

function writeUserlist(rawStr) {
    var indexOfNext = -1;
    var remaining = rawStr;
    var usernames = new Array();
    while (remaining.search(":") != -1) {
        var index = remaining.search(":");
        var nextPiece = remaining.substring(0, index);
        usernames.push(nextPiece);
        remaining = remaining.substring(index + 1, remaining.length);
    }
    usernames.push(remaining);
    userListID.textContent = "";
    var i = 0;
    for (i = 0; i < usernames.length; i++) {
        userListID.textContent = userListID.textContent + usernames[i];
        if (i < (usernames.length - 1)) {
            userListID.textContent = userListID.textContent + "\n";
        }
    }
}

function goodbye() {
    chatWebsocket.close();
    chatWebsocket = null;
}

window.addEventListener("load", init, false);
window.addEventListener("beforeunload", goodbye, false);


