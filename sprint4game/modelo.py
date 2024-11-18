import random
import time
import os

class GameModel:
    def __init__(self, difficulty, player_name, resources):
        self.difficulty = difficulty
        self.player_name = player_name
        self.resources = resources
        self.board = []
        self.card_values = []
        self.moves = 0
        self.start_time = None
        self.selected_cards = []
        self.generate_board()

    def generate_board(self):
        """Genera el tablero y asigna imágenes a las cartas."""
        if self.difficulty == "facil":
            num_pairs = 8
        elif self.difficulty == "medio":
            num_pairs = 18
        elif self.difficulty == "dificil":
            num_pairs = 32
        else:
            raise ValueError("Dificultad no válida")

        self.card_values = [f"C{i+1}" for i in range(num_pairs)] * 2
        random.shuffle(self.card_values)

        size = int(len(self.card_values) ** 0.5)
        self.board = [self.card_values[i:i + size] for i in range(0, len(self.card_values), size)]

    def start_timer(self):
        """Inicia el temporizador."""
        self.start_time = time.time()

    def get_time_elapsed(self):
        """Retorna el tiempo transcurrido desde el inicio del juego."""
        if self.start_time:
            return round(time.time() - self.start_time, 1)
        return 0.0

    def select_card(self, x, y):
        """Gestiona la selección de cartas y retorna si son coincidentes."""
        if len(self.selected_cards) < 2:
            self.selected_cards.append((x, y))

        if len(self.selected_cards) == 2:
            return self.check_match()
        return None

    def check_match(self):
        """Compara las dos cartas seleccionadas."""
        x1, y1 = self.selected_cards[0]
        x2, y2 = self.selected_cards[1]

        if self.board[x1][y1] == self.board[x2][y2]:
            self.selected_cards = []  # Restablecer las cartas seleccionadas
            self.moves += 1
            return True
        else:
            self.selected_cards = []
            self.moves += 1
            return False

    def is_game_complete(self):
        """Verifica si todas las cartas han sido descubiertas."""
        return all(all(card == "" for card in row) for row in self.board)

    def save_score(self):
        """Guarda el puntaje en un archivo."""
        if not os.path.exists("ranking.txt"):
            with open("ranking.txt", "w") as file:
                file.write("Nombre,Dificultad,Movimientos,Tiempo\n")

        with open("ranking.txt", "a") as file:
            time_taken = self.get_time_elapsed()
            file.write(f"{self.player_name},{self.difficulty},{self.moves},{time_taken}\n")

    def load_scores(self):
        """Carga las estadísticas guardadas."""
        scores = []
        if os.path.exists("ranking.txt"):
            with open("ranking.txt", "r") as file:
                for line in file.readlines()[1:]:
                    name, difficulty, moves, time = line.strip().split(",")
                    scores.append((name, int(moves), float(time)))
        return scores

    def get_card_image(self, x, y):
        """Devuelve la imagen de la carta para las posiciones dadas (x, y)."""
        card_value = self.board[x][y]
        return self.resources.get_card_image(card_value)