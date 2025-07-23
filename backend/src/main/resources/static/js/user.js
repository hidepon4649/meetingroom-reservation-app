document.addEventListener('DOMContentLoaded', () => {
  document.querySelectorAll('.edit-button').forEach((button) => {
    button.addEventListener('click', () => {
      const userId = button.getAttribute('data-id');
      const url = `/admin/user/${encodeURIComponent(userId)}`;
      window.location.href = url;
    });
  });
});

document.addEventListener('DOMContentLoaded', () => {
  document.querySelectorAll('.btn-cancel').forEach((btn) => {
    btn.addEventListener('click', (event) => {
      event.preventDefault(); // フォーム送信防止
      window.location.href = '/admin/user'; // 画面遷移
    });
  });
});

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
