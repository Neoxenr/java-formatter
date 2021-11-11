package it.sevenbits.fourthworkshop.sm.manager;

import it.sevenbits.fourthworkshop.sm.network.INetwork;
import it.sevenbits.fourthworkshop.sm.network.NetworkPackage;

public class SimpleNetworkManager implements INetworkManager {
    private boolean isInterrupted = false;
    private final long TIMEOUT = 1000;

    /**
     *
     * @param network - input network
     * @throws NetworkManagerException - if network manager was interrupted unexpectedly
     */
    public void listen(final INetwork network) throws NetworkManagerException {
        boolean isListen = false;
        StringBuilder buffer = new StringBuilder();
        int trashPackagesCount = 0;
        try {
            while (!isInterrupted) {
                while (network.hasPackage()) {
                    NetworkPackage p = network.getPackage();
                    if (!isListen && p.getType().equals("MESSAGE")) {
                        isListen = true;
                        System.out.println("START LISTENING");
                    }

                    if (isListen && p.getType().equals("MESSAGE")) {
                        System.out.println("MESSAGE ADDED: " + p.getMessage());
                        buffer.append(p.getMessage());
                    }

                    if (isListen && p.getType().equals("TRASH")) {
                        trashPackagesCount++;
                        if (trashPackagesCount > 1) {
                            trashPackagesCount = 0;
                            isListen = false;
                            System.out.println(buffer.toString());
                            buffer.setLength(0);
                            System.out.println("STOP LISTENING");
                            continue;
                        }
                        System.out.println("TRASH IGNORED ON LISTENING");
                    }

                    if (!isListen && p.getType().equals("TRASH")) {
                        System.out.println("TRASH IGNORED");
                        continue;
                    }
                }

                Thread.sleep(TIMEOUT);
            }
        } catch (InterruptedException e) {
            throw new NetworkManagerException("Network manager was interrupted unexpectedly", e);
        }
    }

    /**
     * network manager stop
     */
    public void stop() {
        this.isInterrupted = true;
    }
}
