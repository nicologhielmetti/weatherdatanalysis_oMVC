package Resolvers;

import Actions.Action;
import Policies.Policy;

public interface Resolver {
    Policy resolve(Action a);
}
