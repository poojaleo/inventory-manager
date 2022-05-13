class AuthService {
    getCurrentCompanyName() {
        const companyName = sessionStorage.getItem('companyName');
        if(companyName === 'undefined' || !companyName) {
            return null;
        } else {
            return companyName;
        }
    }

    setUserSession(companyName) {
        sessionStorage.setItem('companyName', companyName);
    }

    resetUserSession() {
        sessionStorage.removeItem('companyName');
    }
}

export default new AuthService();