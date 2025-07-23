document.addEventListener('DOMContentLoaded', () => {
  const form = document.getElementById('editForm');

  let editingRow = null; // 編集中の行

  document.querySelectorAll('.edit-button').forEach((btn) => {
    btn.addEventListener('click', () => {
      if (editingRow) {
        alert('他の行が編集中です。先に保存またはキャンセルしてください。');
        return;
      }

      const tr = btn.closest('tr');
      if (!tr) return;

      editingRow = tr;
      // 他の編集ボタンを無効化。ただし自分のボタンは有効
      document.querySelectorAll('.edit-button').forEach((b) => {
        b.disabled = true;
      });
      btn.disabled = false;

      const cells = tr.querySelectorAll('td');
      const id = btn.dataset.id;
      const isAdmin = btn.dataset.admin === 'true' || btn.dataset.admin === '◯';
      const name = btn.dataset.name;
      const department = btn.dataset.department;
      const email = btn.dataset.email;
      const tel = btn.dataset.tel;
      const photo = btn.dataset.photo;

      // セルをinput要素に差し替え（1列目idは表示のまま）
      cells[1].innerHTML = `<input type="checkbox" ${
        isAdmin ? 'checked' : ''
      } />`;
      cells[2].innerHTML = `<input type="text" value="${name}" class="form-control" />`;
      cells[3].innerHTML = `<input type="text" value="${department}" class="form-control" />`;
      cells[4].innerHTML = `<input type="email" value="${email}" class="form-control" />`;
      cells[5].innerHTML = `<input type="tel" value="${tel}" class="form-control" />`;
      //   cells[6].innerHTML = `<input type="file" value="${}" class="form-control" />`;
      cells[7].innerHTML = `
        <div class="d-flex">
        <button class="btn btn-save btn-success mx-1">保存</button>
        <button class="btn btn-cancel btn-secondary mx-1">取消</button>
        </div>
      `;

      // 保存処理
      cells[7].querySelector('.btn-save').addEventListener('click', () => {
        form.action = `${form.action.split('?')[0]}/${id}`; // URL末尾にid付与（既存ロジック踏襲）
        form.id.value = id;
        form.admin.value = cells[1].querySelector('input').checked;
        form.name.value = cells[2].querySelector('input').value;
        form.department.value = cells[3].querySelector('input').value;
        form.email.value = cells[4].querySelector('input').value;
        form.tel.value = cells[5].querySelector('input').value;

        form.submit();
      });

      // キャンセル処理
      cells[7].querySelector('.btn-cancel').addEventListener('click', () => {
        location.reload();
      });

      // 編集モード用に元のデータを保存（キャンセル時復元用）
      tr.dataset.admin = isAdmin ? '◯' : '';
      tr.dataset.name = name;
      tr.dataset.department = department;
      tr.dataset.email = email;
      tr.dataset.tel = tel;
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
