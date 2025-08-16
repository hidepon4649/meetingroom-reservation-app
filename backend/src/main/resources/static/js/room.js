// -----------------
// 会議室画面
// -----------------

// 編集ボタン
document.addEventListener('DOMContentLoaded', () => {
  document.querySelectorAll('.room-edit-button').forEach((btn) => {
    btn.addEventListener('click', () => {
      const url = `/admin/room/${btn.dataset.id}?page=${btn.dataset.page}&size=${btn.dataset.size}`;
      window.location.href = url;
    });
  });
});

// 取消ボタン
document.addEventListener('DOMContentLoaded', () => {
  document.querySelectorAll('.room-cancel-button').forEach((btn) => {
    btn.addEventListener('click', (event) => {
      event.preventDefault(); // フォーム送信防止
      window.location.href = `/admin/room?page=${btn.dataset.page}&size=${btn.dataset.size}`; // 画面遷移
    });
  });
});

// 削除ボタン
document.addEventListener('DOMContentLoaded', () => {
  document.querySelectorAll('.room-delete-button').forEach((btn) => {
    btn.addEventListener('click', () => {
      if (!confirm('本当に削除しますか？')) return;

      const form = document.getElementById('deleteForm');
      form.action = `${form.action}/${btn.dataset.id}?page=${btn.dataset.page}&size=${btn.dataset.size}`;
      form.submit();
    });
  });
});
