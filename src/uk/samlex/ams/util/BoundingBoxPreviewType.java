package uk.samlex.ams.util;

public enum BoundingBoxPreviewType {
    CORNERS {
        @Override
        public String toString() {
            return "corners";
        }
    },
    FILL {
        @Override
        public String toString() {
            return "fill";
        }
    },
    OUTLINE {
        @Override
        public String toString() {
            return "outline";
        }
    },
    WALLS {
        @Override
        public String toString() {
            return "walls";
        }
    }
}
