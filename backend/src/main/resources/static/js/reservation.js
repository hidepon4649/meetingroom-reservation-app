// -----------------
// 会議室予約画面
// -----------------

// 編集ボタン
document.addEventListener('DOMContentLoaded', () => {
  document.querySelectorAll('.reservation-edit-button').forEach((btn) => {
    btn.addEventListener('click', () => {
      const url = `/reservation/${btn.dataset.id}`;
      window.location.href = url;
    });
  });
});

// 取消ボタン
document.addEventListener('DOMContentLoaded', () => {
  document.querySelectorAll('.reservation-cancel-button').forEach((btn) => {
    btn.addEventListener('click', (event) => {
      event.preventDefault(); // フォーム送信防止
      window.location.href = '/reservation'; // 画面遷移
    });
  });
});

// 削除ボタン
document.addEventListener('DOMContentLoaded', () => {
  document.querySelectorAll('.reservation-delete-button').forEach((btn) => {
    btn.addEventListener('click', () => {
      if (!confirm('本当に削除しますか？')) return;

      const form = document.getElementById('deleteForm');
      form.action = `${form.action}/${btn.dataset.id}`;
      form.id.value = btn.dataset.id;
      form.submit();
    });
  });
});
