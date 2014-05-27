package uk.samlex.ams.config;

public enum HeightLimitMode {

    ABOVE {
        @Override
        public String toString() {
            return "above";
        }
    },
    BELOW {
        @Override
        public String toString() {
            return "below";
        }
    }
}
