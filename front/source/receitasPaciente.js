document.addEventListener("DOMContentLoaded", () => {
  const receitas = [
    {
      medico: "Dr. João Silva",
      validade: "2025-06-10",
      link: "#",
    },
    {
      medico: "Dra. Fernanda Lima",
      validade: "2025-06-15",
      link: "#",
    },
  ];

  const container = document.getElementById("lista-receitas");

  receitas.forEach((rec) => {
    const item = document.createElement("div");
    item.className = "bg-white rounded-xl shadow p-4 border-l-4 border-green-400";

    item.innerHTML = `
      <h2 class="text-lg font-semibold text-gray-800">Emitida por ${rec.medico}</h2>
      <p class="text-sm text-gray-500">Válida até: ${rec.validade}</p>
      <a href="${rec.link}" class="mt-2 inline-block text-sm text-green-600 hover:underline">
        Baixar Receita
      </a>
    `;

    container.appendChild(item);
  });
});
