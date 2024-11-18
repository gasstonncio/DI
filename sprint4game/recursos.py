import requests
from PIL import Image, ImageTk
import io

from PIL.Image import Resampling

class Recursos:
    def __init__(self, root, card_width=100, card_height=150):
        self.root = root
        self.card_images = {}
        self.back_image = None
        self.card_width = card_width
        self.card_height = card_height
        self.load_images()

    def load_images(self):
        """Carga las imágenes necesarias para el juego."""
        try:
            # Base URL
            base_url = "https://raw.githubusercontent.com/gasstonncio/DI/refs/heads/main/sprint4game/imagenes/carta_{}.png"
            back_url = "https://raw.githubusercontent.com/gasstonncio/DI/refs/heads/main/sprint4game/uno_card_back.png"

            self.back_image = self.load_image(back_url)

            # Generar las URLs de las cartas automáticamente
            for i in range(1, 33):
                url = base_url.format(i)
                self.card_images[f"C{i}"] = self.load_image(url)

        except Exception as e:
            print(f"Error al descargar o procesar las imágenes: {e}")

    def load_image(self, url):
        """Descarga y carga una imagen desde una URL."""
        try:
            response = requests.get(url)
            image = Image.open(io.BytesIO(response.content))
            image = image.resize((self.card_width, self.card_height), Resampling.LANCZOS)
            return ImageTk.PhotoImage(image)
        except Exception as e:
            print(f"Error al cargar la imagen desde {url}: {e}")
            return None

    def get_card_image(self, card_value):
        """Devuelve la imagen de una carta dada su valor."""
        return self.card_images.get(card_value, self.back_image)

    def get_back_image(self):
        """Devuelve la imagen de la parte posterior de la carta."""
        return self.back_image