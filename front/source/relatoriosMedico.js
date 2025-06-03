document.addEventListener("DOMContentLoaded", () => {
  const relatorios = [
    {
      tipo: "Relatório de Consultas",
      data: "2025-06-01",
      link: "#"
    },
    {
      tipo: "Relatório de Exames",
      data: "2025-05-28",
      link: "#"
    }
  ];

  const container = document.getElementById("lista-relatorios");

  relatorios.forEach((relatorio) => {
    const item = document.createElement("div");
    item.className = "bg-white rounded-xl shadow p-4 border-l-4 border-blue-400";

    item.innerHTML = `
      <h2 class="text-lg font-semibold text-gray-800">${relatorio.tipo}</h2>
      <p class="text-sm text-gray-500">Data: ${relatorio.data}</p>
      <a href="${relatorio.link}" class="text-sm text-blue-500 hover:underline mt-2 inline-block">
        Exportar PDF
      </a>
    `;
    container.appendChild(item);
  });
});
