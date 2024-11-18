import random
import json
import os

class GameModel:
    def __init__(self):
        self.board = []
        self.move_count = 0
        self.time_elapsed = 0
        self.player_name = ""
        self.difficulty = ""
        self.first_card = None
        self.second_card = None
        self.stats_file = "stats.json"
        self.card_values = []

    def setup_game(self, player_name, difficulty):
        """Configura el juego con el nombre del jugador y la dificultad."""
        self.player_name = player_name
        self.difficulty = difficulty
        self.board = self.generate_board(difficulty)
        self.move_count = 0
        self.time_elapsed = 0
        self.first_card = None
        self.second_card = None

    def generate_board(self, difficulty):
        """Genera el tablero de cartas según la dificultad."""
        if difficulty == 'facil':
            size = 4
        elif difficulty == 'medio':
            size = 6
        else:  # dificil
            size = 8

        cards = [f"C{i}" for i in range(1, (size * size // 2) + 1)]
        board = cards + cards  # Duplicar las cartas para formar pares
        random.shuffle(board)  # Mezclar las cartas
        self.card_values = board  # Guardar los valores de las cartas
        hidden_board = [["" for _ in range(size)] for _ in range(size)]
        return hidden_board

    def process_card_click(self, x, y):
        """Procesa el clic en una carta."""
        if self.board[x][y] != "" or (self.first_card and self.second_card):
            return False

        if not self.first_card:
            self.first_card = (x, y)
            self.board[x][y] = self.card_values[x * len(self.board) + y]  # Mostrar la carta
            return True
        elif not self.second_card:
            self.second_card = (x, y)
            self.move_count += 1
            self.board[x][y] = self.card_values[x * len(self.board) + y]  # Mostrar la carta

            if self.board[self.first_card[0]][self.first_card[1]] == self.board[self.second_card[0]][self.second_card[1]]:
                self.first_card = None
                self.second_card = None
                if self.check_game_over():
                    return "game_over"
            else:
                return "hide_cards"
            return True
        return False

    def hide_cards(self):
        """Oculta las cartas si no coinciden."""
        self.board[self.first_card[0]][self.first_card[1]] = ""
        self.board[self.second_card[0]][self.second_card[1]] = ""
        self.first_card = None
        self.second_card = None

    def check_game_over(self):
        """Verifica si todas las cartas han sido emparejadas."""
        for row in self.board:
            if "" in row:
                return False
        return True

    def get_stats(self):
        """Devuelve las estadísticas del juego."""
        return f"Movimientos: {self.move_count}, Tiempo: {self.time_elapsed}"

    def save_stats(self):
        """Guarda las estadísticas del juego en un archivo JSON."""
        stats = {
            "player_name": self.player_name,
            "difficulty": self.difficulty,
            "move_count": self.move_count,
            "time_elapsed": self.time_elapsed
        }
        try:
            with open(self.stats_file, "a") as file:
                json.dump(stats, file)
                file.write("\n")  # Añadir nueva línea para cada entrada
        except IOError as e:
            print(f"Error al guardar las estadísticas: {e}")

    def load_stats(self):
        """Carga las estadísticas del juego desde un archivo JSON."""
        if not os.path.exists(self.stats_file):
            return []
        try:
            with open(self.stats_file, "r") as file:
                stats_list = [json.loads(line) for line in file]
            return stats_list
        except IOError as e:
            print(f"Error al cargar las estadísticas: {e}")
            return []