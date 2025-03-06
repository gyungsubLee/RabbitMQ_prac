const socket = new SockJS('/ws'); // 서버의 WebSocket Endpoint 연결
const stompClient = Stomp.over(socket); // SockJS 객체를 STOMP 클라이언트로 wrapping

stompClient.connect({}, function () {
    console.log('Connected to WebSocket');

    // 서버로부터 메시지 구독
    stompClient.subscribe('/sub/notifications', function (message) { // ✅ subscribe 메서드 수정
        const notificationsDiv = document.getElementById('notifications');
        if (notificationsDiv) { // ✅ notificationsDiv가 존재하는지 확인
            const newNotification = document.createElement('div');
            newNotification.textContent = message.body;
            notificationsDiv.appendChild(newNotification);
        }
    });

    // 폼이 존재하는 경우, 메시지 전송 이벤트 추가
    const form = document.getElementById('notificationForm');
    if (form) { // ✅ form이 존재하는지 확인
        form.addEventListener('submit', function (event) {
            event.preventDefault();
            const messageInput = document.getElementById('notificationMessage');
            if (messageInput) { // ✅ messageInput이 존재하는지 확인
                const message = messageInput.value;

                stompClient.send('/pub/send', {}, JSON.stringify({ message: message }));
                messageInput.value = '';
            }
        });
    }
});