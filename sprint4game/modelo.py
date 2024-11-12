import random
import time
import os


class GameModel:
    def __init__(self, difficulty, player_name):
        # Inicializar atributos
        self.difficulty = difficulty
        self.player_name = player_name
        self.board = []
        self.card_values = []
        self.moves = 0
        self.start_time = None
        self.end_time = None
        self.images_loaded = False
        self.selected_cards = []

        # Definir las cartas en función de la dificultad
        self.generate_board()

    def generate_board(self):
        """
        Genera el tablero basado en la dificultad seleccionada.
        El tablero se genera con cartas duplicadas para que haya parejas.
        """
        if self.difficulty == "facil":
            num_pairs = 6
        elif self.difficulty == "medio":
            num_pairs = 8
        elif self.difficulty == "dificil":
            num_pairs = 10
        else:
            raise ValueError("Dificultad no válida")

        # Las cartas son valores duplicados (como una lista de pares)
        self.card_values = [f"Card {i + 1}" for i in range(num_pairs)] * 2
        random.shuffle(self.card_values)

        # Crear el tablero como una lista de listas (cuadrícula)
        self.board = []
        size = int(len(self.card_values) ** 0.5)  # Asumimos que el tablero tiene forma cuadrada
        for i in range(size):
            row = self.card_values[i * size:(i + 1) * size]
            self.board.append(row)

    def start_timer(self):
        """
        Comienza el temporizador cuando el juego empieza.
        """
        self.start_time = time.time()

    def get_time_elapsed(self):
        """
        Retorna el tiempo transcurrido desde que comenzó el juego.
        """
        if self.start_time:
            return round(time.time() - self.start_time, 2)
        return 0.0

    def select_card(self, i, j):
        """
        Marca una carta seleccionada. Si se seleccionan dos cartas, comprueba si son iguales.
        """
        if len(self.selected_cards) < 2:
            self.selected_cards.append((i, j))

        # Si ya se han seleccionado dos cartas, comprueba si coinciden
        if len(self.selected_cards) == 2:
            return self.check_match()
        return None

    def check_match(self):
        """
        Verifica si las dos cartas seleccionadas son iguales.
        """
        i1, j1 = self.selected_cards[0]
        i2, j2 = self.selected_cards[1]

        if self.board[i1][j1] == self.board[i2][j2]:
            # Si coinciden, las cartas siguen reveladas
            self.selected_cards = []
            self.moves += 1
            return True
        else:
            # Si no coinciden, las cartas se vuelven a ocultar
            self.selected_cards = []
            self.moves += 1
            return False

    def is_game_complete(self):
        """
        Verifica si el juego ha terminado (si todas las cartas están emparejadas).
        """
        return all(all(card == "" for card in row) for row in self.board)

    def load_images(self):
        """
        Simula la carga de imágenes. El modelo manejaría la descarga de las imágenes reales en el caso de necesitar recursos externos.
        """
        self.images_loaded = True

    def save_score(self):
        """
        Guarda el puntaje del jugador en un archivo de estadísticas.
        """
        if not os.path.exists("ranking.txt"):
            with open("ranking.txt", "w") as file:
                file.write("Nombre,Dificultad,Movimientos,Tiempo\n")

        with open("ranking.txt", "a") as file:
            time_taken = self.get_time_elapsed()
            file.write(f"{self.player_name},{self.difficulty},{self.moves},{time_taken}\n")

    def load_scores(self):
        """
        Carga las puntuaciones del archivo de estadísticas.
        """
        scores = {"facil": [], "medio": [], "dificil": []}

        if os.path.exists("ranking.txt"):
            with open("ranking.txt", "r") as file:
                lines = file.readlines()[1:]  # Omitir encabezado
                for line in lines:
                    name, difficulty, moves, time = line.strip().split(",")
                    scores[difficulty].append((name, int(moves), float(time)))

            # Ordenar por menor número de movimientos y menor tiempo
            for difficulty in scores:
                scores[difficulty] = sorted(scores[difficulty], key=lambda x: (x[1], x[2]))[:3]  # Top 3

        return scores
