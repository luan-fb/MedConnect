document.addEventListener('DOMContentLoaded', function () {
  console.log('JS carregado!');  
  const form = document.querySelector('form');

  form.addEventListener('submit', function (e) {
    e.preventDefault();

    const nome = form.nome.value.trim();
    const email = form.email.value.trim();
    const cpf = form.cpf.value.trim();
    const dataNascimento = form.nascimento.value;
    const senha = form.senha.value;
    const confirmarSenha = form.confirmarSenha.value;

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const cpfNumeros = cpf.replace(/\D/g, '');
    const hoje = new Date();
    const nascimento = new Date(dataNascimento);

    if (nome.length < 3) {
      alert('Nome inválido. Deve conter ao menos 3 letras.');
      return;
    }

    if (!emailRegex.test(email)) {
      alert('E-mail inválido.');
      return;
    }

    if (cpfNumeros.length !== 11) {
      alert('CPF deve conter exatamente 11 números.');
      return;
    }

    if (!dataNascimento || nascimento >= hoje) {
      alert('Data de nascimento inválida.');
      return;
    }

    if (senha.length < 6) {
      alert('A senha deve conter pelo menos 6 caracteres.');
      return;
    }

    if (senha !== confirmarSenha) {
      alert('As senhas não coincidem.');
      return;
    }

    alert('Cadastro simulado com sucesso!');
    window.location.href = 'login.html';
  });
});
