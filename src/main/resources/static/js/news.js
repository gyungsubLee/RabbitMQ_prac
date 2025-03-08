let stompClient;
let subscription = null;

function connect() {
    const newsType = document.getElementById("newsType").value;

    // WebSocket 연결
    if (!stompClient) {
        const socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, function () {
            console.log("Connected to WebSocket");
            sendNewsType(newsType);
            subscribeToNews(newsType);
        });
    } else if (stompClient.connected) {
        sendNewsType(newsType); // Send selected news type if already connected
        subscribeToNews(newsType); // Re-subscribe to the new topic
    } else {
        console.error("소켓 연결이 안되었습니다.");
    }
}

// 서버에 선택한 뉴스 타입 전송
function sendNewsType(newsType) {
    // Send the selected news type to the server
    stompClient.send("/app/subscribe", {'newsType': newsType}, "");
    console.log(`Sent newsType to server: ${newsType}`);
}

// 이전 토픽에서 구독 취소, 새 토픽 구독
function subscribeToNews(newsType) {
    // 이전 구독된 내용이 존재하면 unsubscribe
    if (subscription) {
        subscription.unsubscribe();
        console.log("Previous subscription cancelled.");
    }
    subscription = stompClient.subscribe(`/topic/${newsType}`, function (message) {
        addMessageToDiv(message.body);
    });
    console.log(`Subscribed to /topic/${newsType}`);
}

function addMessageToDiv(message) {
    const newsDiv = document.getElementById("news");
    const newMessage = document.createElement("div");
    newMessage.textContent = message;
    newsDiv.appendChild(newMessage);
}

