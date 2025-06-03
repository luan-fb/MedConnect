document.addEventListener("DOMContentLoaded", function () {
  const form = document.getElementById("cadastro-medico-form");

  form.addEventListener("submit", function (e) {
    e.preventDefault();

    const nome = form.nome.value.trim();
    const crm = form.crm.value.trim();
    const especialidade = form.especialidade.value.trim();
    const email = form.email.value.trim();
    const senha = form.senha.value;
    const confirmarSenha = form.confirmarSenha.value;

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (nome.length < 3) {
      alert("Nome deve conter pelo menos 3 caracteres.");
      return;
    }

    if (crm.length < 4) {
      alert("CRM deve conter pelo menos 4 caracteres.");
      return;
    }

    if (especialidade.length < 3) {
      alert("Especialidade inválida.");
      return;
    }

    if (!emailRegex.test(email)) {
      alert("E-mail inválido.");
      return;
    }

    if (senha.length < 6) {
      alert("A senha deve conter no mínimo 6 caracteres.");
      return;
    }

    if (senha !== confirmarSenha) {
      alert("As senhas não coincidem.");
      return;
    }

    alert("Cadastro simulado com sucesso!");
    window.location.href = "login.html";
  });
});
