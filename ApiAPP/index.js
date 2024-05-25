const express = require('express');
const app = express();

// Definindo uma rota GET
app.get('/validade/payment', (req, res) => {
    res.status(201).send('Pagamento Verificado com Sucesso!');
});

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
console.log(`Servidor rodando na porta ${PORT}`);
});