# Sudoku DIO

Desafio DIO - Criando um Jogo do Sudoku em Java com interface gráfica

Inclusão de Testes Unitários

## 📋 Descrição do Projeto

Este projeto implementa um jogo Sudoku interativo em Java com interface de usuário (UI) e um conjunto completo de testes unitários para garantir a qualidade do código.

## 🧪 Testes Unitários

O projeto inclui 45 testes unitários com cobertura completa das classes de modelo e serviço.

### Testes Implementados

| Classe de Teste | Número de Testes | Cobertura |
|---|---|---|
| **SpaceTest** | 9 | Classe `Space` (célula do tabuleiro) |
| **BoardTest** | 17 | Classe `Board` (tabuleiro completo) |
| **GameStatusEnumTest** | 6 | Enum `GameStatusEnum` (status do jogo) |
| **BoardServiceTest** | 13 | Classe `BoardService` (serviço do jogo) |
| **TOTAL** | **45** | Modelos e serviços |

### O que é testado

✅ Criação e inicialização de objetos  
✅ Alteração e limpeza de valores  
✅ Validação de regras (espaços fixos)  
✅ Detecção de erros  
✅ Estados do jogo (não iniciado, incompleto, completo)  
✅ Reset e reinicialização  
✅ Múltiplas instâncias  
✅ Casos extremos (valores nulos, valores múltiplos, etc.)  

### Executar os Testes

#### Com Maven (recomendado)
```bash
cd sudoku_dio
mvn test
```
