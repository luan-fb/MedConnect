document.addEventListener("DOMContentLoaded", () => {
  const prontuarios = [
    {
      paciente: "Maria Oliveira",
      data: "2025-05-30",
      resumo: "Consulta de rotina, pressão controlada, sem queixas."
    },
    {
      paciente: "Carlos Souza",
      data: "2025-05-29",
      resumo: "Exame de sangue solicitado, retorno agendado."
    }
  ];

  const container = document.getElementById("lista-prontuarios");

  prontuarios.forEach((prontuario) => {
    const item = document.createElement("div");
    item.className = "bg-white rounded-xl shadow p-4 border-l-4 border-green-400";

    item.innerHTML = `
      <h2 class="text-lg font-semibold text-gray-800">${prontuario.paciente}</h2>
      <p class="text-sm text-gray-500">Data: ${prontuario.data}</p>
      <p class="text-sm text-gray-600 mt-1">Resumo: ${prontuario.resumo}</p>
      <a href="#" class="text-sm text-green-500 hover:underline mt-2 inline-block">Ver detalhes</a>
    `;
    container.appendChild(item);
  });
});
