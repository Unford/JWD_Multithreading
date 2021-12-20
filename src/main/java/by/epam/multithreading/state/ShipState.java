package by.epam.multithreading.state;

import by.epam.multithreading.entity.Ship;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum ShipState {
    ARRIVING {
        @Override
        public void next(Ship ship){
            ship.setState(WAITING);
        }

        @Override
        public void prev(Ship ship){
            logger.log(Level.ERROR, "Method prev is unsupported for state {}", this);
            throw new UnsupportedOperationException("Method prev is unsupported for state {}" + this);
        }
    },
    WAITING {
        @Override
        public void next(Ship ship){
            ship.setState(UNLOADING);
        }

        @Override
        public void prev(Ship ship){
            ship.setState(ARRIVING);
        }
    },
    UNLOADING {
        @Override
        public void next(Ship ship){
            ship.setState(LOADING);

        }
        @Override
        public void prev(Ship ship){
            ship.setState(WAITING);
        }
    },
    LOADING {
        @Override
        public void next(Ship ship){
            ship.setState(END);
        }
        @Override
        public void prev(Ship ship){
            ship.setState(UNLOADING);
        }
    },
    END {
        @Override
        public void next(Ship ship){
            logger.log(Level.ERROR, "Method next is unsupported for state {}", this);
            throw new UnsupportedOperationException("Method next is unsupported for state {}" + this);
        }
        @Override
        public void prev(Ship ship){
            ship.setState(LOADING);
        }
    };

   private static final Logger logger = LogManager.getLogger();

   public abstract void next(Ship ship);
   public abstract void prev(Ship ship);
}
