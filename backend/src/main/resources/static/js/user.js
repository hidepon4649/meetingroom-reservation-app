// ユーザ編集ボタン
document.addEventListener('DOMContentLoaded', () => {
  document.querySelectorAll('.edit-button').forEach((btn) => {
    btn.addEventListener('click', () => {
      const url = `/admin/user/${btn.dataset.id}`;
      window.location.href = url;
    });
  });
});

// 取消ボタン(ユーザ編集)
document.addEventListener('DOMContentLoaded', () => {
  document.querySelectorAll('.btn-cancel').forEach((btn) => {
    btn.addEventListener('click', (event) => {
      event.preventDefault(); // フォーム送信防止
      window.location.href = '/admin/user'; // 画面遷移
    });
  });
});

// ユーザ削除ボタン
document.addEventListener('DOMContentLoaded', () => {
  document.querySelectorAll('.delete-button').forEach((btn) => {
    btn.addEventListener('click', () => {
      if (!confirm('本当に削除しますか？')) return;

      const form = document.getElementById('deleteForm');
      form.action = `${form.action}/${btn.dataset.id}`;
      form.id.value = btn.dataset.id;
      form.submit();
    });
  });
});
